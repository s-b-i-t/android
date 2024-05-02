package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent.databinding.FragmentCrimeDetailBinding
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"

class CrimeListFragment: Fragment() {
    private val crimeListViewModel : CrimeListViewModel by viewModels()
    private var job : Job? = null

    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "cant get binding since null"
        }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        binding.crimeRecyclerView.layoutManager=LinearLayoutManager(context)
        return binding.root
    }



override fun onViewCreated(view:View, savedInstanceState: Bundle?){
    super.onViewCreated(view, savedInstanceState)
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            val crimes = crimeListViewModel.loadCrimes()
            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
        }
    }
}

override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}

//    override fun onStart(){
//        super.onStart()
//        job = viewLifecycleOwner.lifecycleScope.launch{
//            val crimes = crimeListViewModel.loadCrimes()
//            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
//        }
//        Log.d(TAG, "Job launching in onStart")
//    }
//
//    override fun onStop() {
//        Log.d(TAG, "Job cancelled in onStop")
//        super.onStop()
//
//        job?.cancel()
//    }


    override fun onResume(){
        super.onResume()
        job = viewLifecycleOwner.lifecycleScope.launch{
            val crimes = crimeListViewModel.loadCrimes()
            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
        }
        Log.d(TAG, "Job launching in onResume")



    }


//    override fun onPause() {
//        Log.d(TAG, "Job cancelled in onPause")
//        super.onPause()
//
//        job?.cancel()
//    }



}
