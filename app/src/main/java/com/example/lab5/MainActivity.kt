package com.example.lab5

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.lab5.db.MyDbHelper

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDbHelper = MyDbHelper(this)
        val db: SQLiteDatabase = myDbHelper.writableDatabase

        for (i in 1..50)
        {
            val name = "Student $i"
            val weight = (50..100).random().toDouble()
            val height = (150..200).random().toDouble()
            val age = (18..25).random()
            db.execSQL("INSERT INTO Students (name, weight, height, age) VALUES ('$name', $weight, $height, $age)")
        }

        // Вывод данных в TableLayout с сортировкой по возрасту
        val tableLayout: TableLayout = findViewById(R.id.tableLayout)
        tableLayout.isStretchAllColumns = true

        val cursor: Cursor = db.rawQuery("SELECT * FROM Students ORDER BY age", null)

        val row = TableRow(this)
        val layoutParams1 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f)
        val layoutParams2 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        val layoutParams3 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        val layoutParams4 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

        val nameTextView = TextView(this)
        nameTextView.text = "Name"
        nameTextView.setBackgroundResource(R.drawable.cell_border)
        nameTextView.layoutParams = layoutParams1
        nameTextView.textSize = 24f
        row.addView(nameTextView)

        val weightTextView = TextView(this)
        weightTextView.text = "Weight"
        weightTextView.setBackgroundResource(R.drawable.cell_border)
        weightTextView.layoutParams = layoutParams2
        weightTextView.textSize = 24f
        row.addView(weightTextView)

        val heightTextView = TextView(this)
        heightTextView.text = "Height"
        heightTextView.setBackgroundResource(R.drawable.cell_border)
        heightTextView.layoutParams = layoutParams3
        heightTextView.textSize = 24f
        row.addView(heightTextView)

        val ageTextView = TextView(this)
        ageTextView.text = "Age"
        ageTextView.layoutParams = layoutParams4
        ageTextView.setBackgroundResource(R.drawable.cell_border)
        ageTextView.textSize = 24f
        row.addView(ageTextView)

        tableLayout.addView(row)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val weight = cursor.getDouble(cursor.getColumnIndex("weight"))
                val height = cursor.getDouble(cursor.getColumnIndex("height"))
                val age = cursor.getInt(cursor.getColumnIndex("age"))

                val row = TableRow(this)
                val layoutParams1 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2f)
                val layoutParams2 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                val layoutParams3 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                val layoutParams4 = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

                val nameTextView = TextView(this)
                nameTextView.text = name
                nameTextView.setBackgroundResource(R.drawable.cell_border)
                nameTextView.layoutParams = layoutParams1
                nameTextView.textSize = 24f
                row.addView(nameTextView)

                val weightTextView = TextView(this)
                weightTextView.text = weight.toString()
                weightTextView.layoutParams = layoutParams2
                weightTextView.setBackgroundResource(R.drawable.cell_border)
                weightTextView.textSize = 24f
                row.addView(weightTextView)

                val heightTextView = TextView(this)
                heightTextView.text = height.toString()
                heightTextView.layoutParams = layoutParams3
                heightTextView.setBackgroundResource(R.drawable.cell_border)
                heightTextView.textSize = 24f
                row.addView(heightTextView)

                val ageTextView = TextView(this)
                ageTextView.text = age.toString()
                ageTextView.setBackgroundResource(R.drawable.cell_border)
                ageTextView.layoutParams = layoutParams4
                ageTextView.textSize = 24f
                row.addView(ageTextView)

                tableLayout.addView(row)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.execSQL("DELETE FROM Students")
        db.close()
    }
}