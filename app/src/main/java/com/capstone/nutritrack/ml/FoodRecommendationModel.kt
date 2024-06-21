package com.capstone.nutritrack.ml

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import kotlin.math.abs

data class NutrisiData(
    val name: String,
    val image: String,
    val recipe: String,
    val calories: Float,
    val proteins: Float,
    val fat: Float,
    val carbohydrate: Float
)

data class FoodRecommendation(
    val name: String,
    val image: String,
    val recipe: String,
    val calories: Float,
    val proteins: Float,
    val fat: Float,
    val carbohydrate: Float
)

class FoodRecommendationModel(context: Context) {
    private var interpreter: Interpreter? = null

    init {
        try {
            interpreter = Interpreter(loadModelFile(context))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("model.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadNutrisiData(context: Context): List<NutrisiData>? {
        val json: String
        try {
            json = context.assets.open("dataset_nutrisi.json").bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        val type = object : TypeToken<List<NutrisiData>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun getRecommendations(context: Context, calories: Float): List<FoodRecommendation> {
        val adjustedCalories = calories / 5
        val input = floatArrayOf(adjustedCalories)
        val output = Array(1) { FloatArray(4) } // Menghasilkan output dengan shape [1, 4]

        interpreter?.run(input, output)

        // Load JSON data
        val nutrisiDataList = loadNutrisiData(context) ?: return emptyList()

        // Memproses output dari model TensorFlow Lite
        val recommendations = mutableListOf<FoodRecommendation>()
        val calorieValues = output[0]

        // Cari data berdasarkan kalori yang paling mendekati
        calorieValues.forEach { calorieValue ->
            val matchedNutrisiData = nutrisiDataList.minByOrNull { abs(it.calories - calorieValue) }
            if (matchedNutrisiData != null) {
                recommendations.add(
                    FoodRecommendation(
                        name = matchedNutrisiData.name,
                        image = matchedNutrisiData.image,
                        recipe = matchedNutrisiData.recipe,
                        calories = matchedNutrisiData.calories,
                        proteins = matchedNutrisiData.proteins,
                        fat = matchedNutrisiData.fat,
                        carbohydrate = matchedNutrisiData.carbohydrate
                    )
                )
            }
        }
        return recommendations
    }
}