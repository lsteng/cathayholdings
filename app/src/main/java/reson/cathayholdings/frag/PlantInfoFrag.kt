package reson.cathayholdings.frag

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.frag_plant_info.view.*
import kotlinx.android.synthetic.main.item_sub_info.view.*
import reson.cathayholdings.MainApplication
import reson.cathayholdings.R
import reson.cathayholdings.data.PlantSubResult

private const val ARG_plantInfo = "param1"

class PlantInfoFrag: Fragment() {
    val TAG = "PlantInfoFrag"
    lateinit var mContext: Context
    lateinit var plantInfo: PlantSubResult

    companion object {
        val FRAG_TRANS_NAME = "PlantInfoFrag"

        @JvmStatic
        fun newInstance(plantInfo: Parcelable) =
            PlantInfoFrag().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_plantInfo, plantInfo)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            plantInfo = it.getParcelable<PlantSubResult>(ARG_plantInfo)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_plant_info, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = plantInfo.F_Name_Ch
        MainApplication.imageLoader.displayImage(plantInfo.F_Pic01_URL, view.imgIV)
//        view.imgIV.load(plantInfo.F_Pic01_URL)
//        Glide.with(this).load(plantInfo.F_Pic01_URL).into(view.imgIV)
        view.nameLayout.titleTV.text = plantInfo.F_Name_Ch
        view.nameLayout.describeTV.text = plantInfo.F_Name_En
        view.aliasLayout.titleTV.text = "別名"
        view.aliasLayout.describeTV.text = plantInfo.F_AlsoKnown
        view.infoLayout.titleTV.text = "簡介"
        view.infoLayout.describeTV.text = plantInfo.F_Brief
        view.knowLayout.titleTV.text = "辨識方式"
        view.knowLayout.describeTV.text = plantInfo.F_Feature
        view.appLayout.titleTV.text = "功能性"
        view.appLayout.describeTV.text = plantInfo.F_Function
        view.dateTV.text = "最後更新：${plantInfo.F_Update}"

        return view
    }

}