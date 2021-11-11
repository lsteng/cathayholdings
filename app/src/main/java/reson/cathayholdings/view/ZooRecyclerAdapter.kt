package reson.cathayholdings.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.item_zoo.view.*
import reson.cathayholdings.MainApplication
import reson.cathayholdings.R
import reson.cathayholdings.data.ZooSubResult
import reson.cathayholdings.frag.ZooInfoFrag
import reson.chocomedia.util.FragTransUtil

class ZooRecyclerAdapter(val dataList: List<ZooSubResult>, val activity: Activity, val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ZooItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_zoo, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        if (holder is ZooItemViewHolder){
            val zooSubResult = dataList[pos]
//            MainApplication.imageLoader.displayImage(zooSubResult.E_Pic_URL, holder.imgIV)
            holder.imgIV.load(zooSubResult.E_Pic_URL)
            holder.nameTV.text = zooSubResult.E_Name
            holder.infoTV.text = zooSubResult.E_Info
            holder.memoTV.text = zooSubResult.E_Memo

            holder.itemll.setOnClickListener {
//                (activity as MainActivity).hideKeyboard()
//                val intent = Intent(activity, VideoInfoActivity::class.java).apply {
//                    putExtra(VideoInfoActivity.Key_thumb, videoInfo.thumb)
//                    putExtra(VideoInfoActivity.Key_name, videoInfo.name)
//                    putExtra(VideoInfoActivity.Key_rating, rateString)
//                    putExtra(VideoInfoActivity.Key_created_at, dateString)
//                    putExtra(VideoInfoActivity.Key_total_views, videoInfo.total_views)
//                }
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, holder.thumbIV, activity.resources.getString(R.string.transition_name))
//                startActivity(activity, intent, options.toBundle())

                FragTransUtil.replaceContentFrag(
                    ZooInfoFrag.newInstance(zooSubResult, activity),
                    ZooInfoFrag.FRAG_TRANS_NAME,
                    fragmentManager)
            }
        }
    }


}

class ZooItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var itemll = view.itemll
    var imgIV = view.imgIV
    var nameTV = view.nameTV
    var infoTV = view.infoTV
    var memoTV = view.memoTV
}