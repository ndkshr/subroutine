package me.ndkshr.subroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.ColorPickerItemBinding
import me.ndkshr.subroutine.modal.HabitLabels

class LabelsAdapter: RecyclerView.Adapter<LabelsAdapter.ColorItemVH>() {

    var colors: List<HabitLabels> = emptyList()

    class ColorItemVH(private val binding: ColorPickerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(label: HabitLabels) {
            binding.labelText.text = label.name
            binding.labelParent.setCardBackgroundColor(
                AppCompatResources.getColorStateList(
                    binding.root.context,
                    R.color.driftwood_yellow
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemVH {
        return ColorItemVH(
            ColorPickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: ColorItemVH, position: Int) {
        holder.bind(colors[position])
    }
}