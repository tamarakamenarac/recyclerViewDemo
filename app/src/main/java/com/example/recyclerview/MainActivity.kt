package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.adapters.RecyclerViewAdapter
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.recyclerViewHelpers.GridLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val list = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16", "Item 17", "Item 18", "Item 19")

        adapter = RecyclerViewAdapter(list)

        val manager = GridLayoutManager(this, LinearLayoutManager.HORIZONTAL, 2, 5, false)
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
    }
}