package reson.cathayholdings.frag

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.frag_zoo_info.*
import kotlinx.android.synthetic.main.frag_zoo_info.view.*
import reson.cathayholdings.MainApplication
import reson.cathayholdings.R
import reson.cathayholdings.data.PlantSubResult
import reson.cathayholdings.data.ZooSubResult
import reson.cathayholdings.view.PlantRecyclerAdapter

private const val ARG_zooInfo = "param1"

class ZooInfoFrag: Fragment() {
    val TAG = "ZooInfoFrag"
    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var zooInfo: ZooSubResult
    lateinit var viewModel: FragViewModel
    var plantRecyclerAdapter: PlantRecyclerAdapter? = null

    companion object {
        val FRAG_TRANS_NAME = "ZooInfoFrag"

        @JvmStatic
        fun newInstance(zooInfo: Parcelable, activity: Activity) =
            ZooInfoFrag().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_zooInfo, zooInfo)
                }
                mActivity = activity
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zooInfo = it.getParcelable<ZooSubResult>(ARG_zooInfo)!!
        }
        viewModel = ViewModelProvider(this).get(FragViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_zoo_info, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = zooInfo.E_Name
//        MainApplication.imageLoader.displayImage(zooInfo.E_Pic_URL, view.imgIV)
        view.imgIV.load(zooInfo.E_Pic_URL)
        view.infoTV.text = zooInfo.E_Info
        view.memoTV.text = zooInfo.E_Memo
        view.categoryTV.text = zooInfo.E_Category
        view.webTV.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(zooInfo.E_URL)));
        }

        viewModel.isShowProgress.observe(viewLifecycleOwner, Observer {
            showProgress(it)
        })

        view.recycler.layoutManager = LinearLayoutManager(mContext)
        view.recycler.setHasFixedSize(true)
        view.recycler.adapter = plantRecyclerAdapter

        viewModel.plantResultList.observe(viewLifecycleOwner, Observer {
            showResult(it, view.recycler)
        })

        return view
    }

    private fun showResult(plantSubResultList: List<PlantSubResult>?, recycler: RecyclerView){
        mActivity.runOnUiThread{
            plantSubResultList.let {
                if (!it.isNullOrEmpty()) {
                    plantRecyclerAdapter = PlantRecyclerAdapter(it, parentFragmentManager)
                    recycler.adapter = plantRecyclerAdapter
                }
                showProgress(false)
            }
        }
    }

    private fun showProgress(isShow: Boolean){
        if (isShow){
            progressBar.visibility = View.VISIBLE
        } else{
            progressBar.visibility = View.GONE
        }
    }
}