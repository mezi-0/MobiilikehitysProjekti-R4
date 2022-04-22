package com.example.sbudget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sbudget.databinding.ActivityMembershipsBinding

class Memberships : Fragment() {

    lateinit var binding: ActivityMembershipsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.activity_memberships, container, false)!!

}