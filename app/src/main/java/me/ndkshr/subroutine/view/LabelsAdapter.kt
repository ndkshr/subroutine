package me.ndkshr.subroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import me.ndkshr.subroutine.R
import me.ndkshr.subroutine.databinding.ColorPickerItemBinding
import me.ndkshr.subroutine.modal.HabitLabels

class LabelsAdapter : RecyclerView.Adapter<LabelsAdapter.ColorItemVH>() {

    var colors: List<HabitLabels> = emptyList()
    var selectedLabel: HabitLabels = HabitLabels.OUTCOME

    inner class ColorItemVH(private val binding: ColorPickerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(label: HabitLabels) {
            binding.labelText.text = label.name
            binding.labelParent.backgroundTintList =
                if (selectedLabel == label) {
                    AppCompatResources.getColorStateList(
                        binding.root.context,
                        R.color.selective_yellow
                    )
                } else {
                    AppCompatResources.getColorStateList(
                        binding.root.context,
                        R.color.rich_dark
                    )
                }

            binding.labelText.setTextColor(
                if (selectedLabel == label) {
                    AppCompatResources.getColorStateList(
                        binding.root.context,
                        R.color.rich_dark
                    )
                } else {
                    AppCompatResources.getColorStateList(
                        binding.root.context,
                        R.color.selective_yellow
                    )
                }
            )


            binding.root.setOnClickListener {
                notifyItemChanged(HabitLabels.entries.indexOf(selectedLabel))
                selectedLabel = label
                notifyItemChanged(HabitLabels.entries.indexOf(selectedLabel))
            }
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

    interface LabelInteraction {
        fun labelSelected(label: HabitLabels)
    }
}