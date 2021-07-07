package com.gusi.androidx.module.constraintlayout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gusi.androidx.R


/**
 * A simple [Fragment] subclass.
 *
 */
class FlowFragment : Fragment() {
    companion object {
        fun newInstance(): FlowFragment {
            var args = Bundle()
            val fragment = FlowFragment()
            fragment.arguments = args;
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flow, container, false)
    }


}
