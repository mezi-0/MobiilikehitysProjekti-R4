package com.example.sbudget

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sbudget.databinding.ActivityMembershipsBinding
import com.example.sbudget.databinding.ActivityMyBudgetBinding

class Memberships : Fragment() {

    lateinit var binding: ActivityMembershipsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.activity_memberships, container, false)!!

}