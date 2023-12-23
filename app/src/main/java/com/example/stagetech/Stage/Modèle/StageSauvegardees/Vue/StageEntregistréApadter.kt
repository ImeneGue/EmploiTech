package com.example.stagetech.Stage.Modèle.StageSauvegardees.Vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stagetech.R
import com.example.stagetech.Stage.Modèle.RechercheStage.Modèle.Stage

class StageEntregistréApadter(private var list_stages: List<Stage>,
                              private val onItemClick: (Stage) -> Unit,
                              private val onButtonClickListener: (Stage) -> Unit
) :
    RecyclerView.Adapter<StageEntregistréApadter.StageViewHolder>() {

    private var stages: List<Stage> = emptyList()

    inner class StageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val logoEntreprise2: ImageView = itemView.findViewById(R.id.logoEntreprise2)
        val titreStage2: TextView = itemView.findViewById(R.id.titreStage2)
        val nomEntreprise2: TextView = itemView.findViewById(R.id.nomEntreprise2)
        val address2: TextView = itemView.findViewById(R.id.address2)
        val modeStage2: TextView = itemView.findViewById(R.id.modeStage2)

        val supprimerStage_btn: ImageButton = itemView.findViewById(R.id.supprimerStage_btn)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedStage = list_stages[position]
                    onItemClick.invoke(clickedStage)
                }
            }
            supprimerStage_btn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val stageSelectionee = list_stages[position]
                    onButtonClickListener.invoke(stageSelectionee)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageEntregistréApadter.StageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.unstagesauvegarde_item, parent, false)
        return StageViewHolder(view)
    }


    override fun onBindViewHolder(holder: StageViewHolder, position: Int) {

        val stage = list_stages[position]


        holder.logoEntreprise2.setImageResource(imagedeLentreprise(stage.nomEntreprise))
        holder.titreStage2.text = list_stages[position].titreStage
        holder.nomEntreprise2.text = list_stages[position].nomEntreprise
        holder.address2.text = list_stages[position].localisation
        holder.modeStage2.text = list_stages[position].modeStage

        holder.supprimerStage_btn.setOnClickListener {
            onButtonClickListener.invoke(stage)

        }

    }



    override fun getItemCount(): Int {
        return list_stages.size
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

    fun updateData(nouvellesStages: List<Stage>) {
        list_stages = nouvellesStages
        notifyDataSetChanged()
    }

}