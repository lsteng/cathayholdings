package reson.cathayholdings

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_refresh.*
import reson.cathayholdings.data.ZooSubResult
import reson.cathayholdings.view.ZooRecyclerAdapter

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var mActivity: Activity
    lateinit var viewModel: MainViewModel
    var zooRecyclerAdapter: ZooRecyclerAdapter? = null

    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount) {
            0 -> finish()
            1 -> {
                supportActionBar?.title = "台北市立動物園"
                supportFragmentManager.popBackStack()
            }
            else -> supportFragmentManager.popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "台北市立動物園"
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mActivity = this

        recycler.layoutManager = LinearLayoutManager(mActivity)
        recycler.setHasFixedSize(true)
        recycler.adapter = zooRecyclerAdapter

        viewModel.isShowProgress.observe(this, Observer {
            showProgress(it)
        })
        viewModel.alertMessage.observe(this, Observer {
            showProgress(false)
            if(!it.isNullOrEmpty()){
                if (zooRecyclerAdapter != null){
                    Snackbar.make(mainRL, it, Snackbar.LENGTH_SHORT).show()
                } else{
                    showRetry(true, it)
                }
            }
        })

        refreshRL.setOnClickListener {
            viewModel.getZooData()
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getZooData()
        }

        viewModel.zooResultList.observe(this, Observer {
            showResult(it)
        })
    }

    private fun showResult(zooSubResultList: List<ZooSubResult>?){
        mActivity.runOnUiThread{
            zooSubResultList.let {
                if (!it.isNullOrEmpty()) {
                    zooRecyclerAdapter = ZooRecyclerAdapter(it, mActivity, supportFragmentManager)
                    recycler.adapter = zooRecyclerAdapter
                }
                showProgress(false)
                showRetry(false, "")
            }
        }
    }

    private fun showRetry(isShow:Boolean, alert: String){
        if (isShow){
            refreshRL.visibility = View.VISIBLE
            alertTV.text = alert
            showProgress(false)
        } else{
            refreshRL.visibility = View.GONE
        }
    }

    private fun showProgress(isShow: Boolean){
        if (isShow){
            progressBar.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = true
        } else{
            progressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
        }
    }
}