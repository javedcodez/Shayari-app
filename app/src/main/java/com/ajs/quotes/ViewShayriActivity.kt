package com.ajs.quotes

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajs.quotes.databinding.ActivityViewShayriBinding
import com.bumptech.glide.Glide


class ViewShayriActivity : AppCompatActivity() {

    private lateinit var binding : ActivityViewShayriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewShayriBinding.inflate(layoutInflater)
        setContentView(binding.root)

       var data = intent.getStringExtra("shayri")
        binding.textView.text = "\" $data \""

        data = intent.getStringExtra("author")
        binding.authorName.text = data

        data = intent.getStringExtra("img")

        binding.imgCopy.setOnClickListener {
            val cm: ClipboardManager =
                applicationContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            cm.setText(binding.textView.text)
            Toast.makeText(applicationContext, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        binding.imgShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, binding.textView.text);
            startActivity(Intent.createChooser(shareIntent,"send to"))
        }

        Glide.with(applicationContext)
            .load(data)
            .into(binding.profileImage)
    }
}