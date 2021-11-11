package reson.cathayholdings.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.item_plant.view.*
import reson.cathayholdings.MainApplication
import reson.cathayholdings.R
import reson.cathayholdings.data.PlantSubResult
import reson.cathayholdings.frag.PlantInfoFrag
import reson.chocomedia.util.FragTransUtil

class PlantRecyclerAdapter(val dataList: List<PlantSubResult>, val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        if (holder is PlantItemViewHolder){
            val plantSubResult = dataList[pos]
//            MainApplication.imageLoader.displayImage(plantSubResult.F_Pic01_URL, holder.imgIV)
            holder.imgIV.load(plantSubResult.F_Pic01_URL)
            holder.nameTV.text = plantSubResult.F_Name_Ch
            holder.aliasTV.text = plantSubResult.F_AlsoKnown

            holder.itemll.setOnClickListener {
                FragTransUtil.replaceContentFrag(
                    PlantInfoFrag.newInstance(plantSubResult),
                    PlantInfoFrag.FRAG_TRANS_NAME,
                    fragmentManager)
            }
        }
    }
}

class PlantItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var itemll = view.itemll
    var imgIV = view.imgIV
    var nameTV = view.nameTV
    var aliasTV = view.aliasTV
}