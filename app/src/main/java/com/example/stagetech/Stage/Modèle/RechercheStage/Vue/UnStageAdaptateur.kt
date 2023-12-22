package com.example.stagetech.Stage.Modèle.RechercheStage.Vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.pésentateur.TrouverStagePrésentateur
import java.util.Locale

class UnStageAdaptateur(
    private var list_stages: List<Stage>,
    private val onItemClick: (Stage) -> Unit,
    private val onButtonClickListener: (Stage) -> Unit
) :
    RecyclerView.Adapter<UnStageAdaptateur.StageViewHolder>() {


    private var stages: List<Stage> = emptyList()
    private var filteredStageList: List<Stage> = emptyList()

    inner class StageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoEntreprise: ImageView = itemView.findViewById(R.id.logoEntreprise)
        val titreStage: TextView = itemView.findViewById(R.id.titreStage)
        val nomEntreprise: TextView = itemView.findViewById(R.id.nomEntreprise)
        val address: TextView = itemView.findViewById(R.id.address)
        val modeStage: TextView = itemView.findViewById(R.id.modeStage)

        val enregistrerStage_btn: ImageButton = itemView.findViewById(R.id.enregistrerStage_btn)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedStage = list_stages[position]
                    onItemClick.invoke(clickedStage)
                }
            }
            enregistrerStage_btn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val stageSelectionee = list_stages[position]
                    onButtonClickListener.invoke(stageSelectionee)
                }
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.un_stage_item, parent, false)
        return StageViewHolder(view)
    }


    override fun onBindViewHolder(holder: StageViewHolder, position: Int) {

        val stage = list_stages[position]


        holder.logoEntreprise.setImageResource(imagedeLentreprise(stage.nomEntreprise))
        holder.titreStage.text = list_stages[position].titreStage
        holder.nomEntreprise.text = list_stages[position].nomEntreprise
        holder.address.text = list_stages[position].localisation
        holder.modeStage.text = list_stages[position].modeStage

       // changerImage(holder.enregistrerStage_btn, estSauvgardee(holder.enregistrerStage_btn))

        holder.enregistrerStage_btn.setOnClickListener {
            onButtonClickListener.invoke(stage)
            changerImage(holder.enregistrerStage_btn)
//            changerImage(holder.enregistrerStage_btn,estSauvgardee(holder.enregistrerStage_btn))
        }

    }

    private fun imagedeLentreprise(nomEntreprise: String): Int {
        return when (nomEntreprise.lowercase()) {
            "cwp" -> R.drawable.cwp
            "workland" -> R.drawable.workland
            "apple" -> R.drawable.apple
            "giro" -> R.drawable.giro
            "paypal" -> R.drawable.paypal
            "samsung" -> R.drawable.samsung
            "desjardins" -> R.drawable.desjardins
            "rbc" -> R.drawable.rbc
            else -> R.drawable.default_logo
        }}

    private fun changerImage(image: ImageButton) {
        val drawableResId = R.drawable.enregistrericon
        image.setBackgroundResource(drawableResId)
    }
//    private fun estSauvgardee(image: ImageButton): Boolean {
//
//        val savedDrawableResId = R.drawable.enregistrericon
//        return image.background.constantState?.let { it == ContextCompat.getDrawable(image.context, savedDrawableResId)?.constantState } ?: true
//
//    }
    override fun getItemCount(): Int {
        return list_stages.size
    }

    fun updateData(nouvellesStages: List<Stage>) {
        list_stages = nouvellesStages
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

                filteredStageList = if (query.isNullOrBlank()) {
                    stages
                } else {
                    stages.filter {
                        it.titreStage.toLowerCase(Locale.getDefault()).contains(query)
                    }
                }

                filterResults.values = filteredStageList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredStageList = results?.values as? List<Stage> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

    fun setData(nouvellesStages: List<Stage>) {
        list_stages = nouvellesStages
        notifyDataSetChanged()
    }

}