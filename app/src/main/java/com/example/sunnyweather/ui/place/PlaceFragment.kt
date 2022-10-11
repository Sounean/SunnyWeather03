package com.example.sunnyweather.ui.place

import android.annotation.SuppressLint
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.databinding.FragmentPlaceBinding

/**

 * @Author : Sounean

 * @Time : On 2022-10-10 16:30

 * @Description : PlaceFragment

 * @Warn :

 */
class PlaceFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    private var _binding: FragmentPlaceBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_place, container, false)
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)

        _binding?.recyclerView?.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        _binding?.recyclerView?.adapter = adapter

        /*
        * 给搜索框添加监听器,如果搜索框不为空，则进行对城市的搜索，然后其实是回调到了下面viewModel.placeLiveData.observe(那里去
        * 如果为空，则隐藏ry显示背景图，重置适配器值
        * */
        _binding?.searchPlaceEdit?.addTextChangedListener { editable: Editable? ->
            val content = editable.toString()
            if (content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                _binding?.recyclerView?.visibility = View.GONE
                _binding?.bgImageView?.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        /*
        * 当地点的livedata变化后，先获取到，然后若不为空则把ry显示出来并且隐藏背景图，最后刷新ry适配器来更新视图
        * */
        viewModel.placeLiveData.observe(this, Observer { result ->
//            result.getOrNull() //返回一个可空类型,若失败则为空,否则为value,更适合喜欢 if(f() == null) 的人
//            result.getOrThrow() // 若失败,则抛出异常,更适合喜欢Exception方式的人
//            result.getOrDefault(defaultValue) // 若失败,则返回defaultValue参数
            val places = result.getOrNull()
            if (places != null){
                _binding?.recyclerView?.visibility = View.VISIBLE
                _binding?.bgImageView?.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity , "未能查询到任何地点", Toast.LENGTH_LONG).show()
                result.exceptionOrNull()
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}