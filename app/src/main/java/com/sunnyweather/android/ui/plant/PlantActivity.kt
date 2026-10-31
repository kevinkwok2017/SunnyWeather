package com.sunnyweather.android.ui.plant

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.ActivityPlantBinding

class PlantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantBinding
    private val viewModel by lazy { ViewModelProvider(this).get(PlantViewModel::class.java) }
    private val adapter = PlantAdapter(
        onPlantClick = { plant -> PlantDetailActivity.actionStart(this, plant) },
        onPlantLongClick = { plant -> showDeleteDialog(plant) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.addPlant) {
                showAddPlantDialog()
                true
            } else {
                false
            }
        }
        binding.plantRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.plantRecyclerView.adapter = adapter
        viewModel.plantList.observe(this) { plants ->
            adapter.submitList(plants)
            binding.emptyText.visibility = if (plants.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showAddPlantDialog() {
        val input = EditText(this).apply {
            hint = "例如：绿萝"
            setSingleLine()
            setPadding(48, 0, 48, 0)
        }
        MaterialAlertDialogBuilder(this)
            .setTitle("添加植物")
            .setView(input)
            .setNegativeButton("取消", null)
            .setPositiveButton("确定") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(this, "请输入植物名称", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addPlant(name)
                }
            }
            .show()
    }

    private fun showDeleteDialog(plant: com.sunnyweather.android.logic.model.Plant) {
        MaterialAlertDialogBuilder(this)
            .setTitle("删除植物")
            .setMessage("确定删除“${plant.name}”吗？")
            .setNegativeButton("取消", null)
            .setPositiveButton("删除") { _, _ ->
                viewModel.deletePlant(plant)
            }
            .show()
    }
}
