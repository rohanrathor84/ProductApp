package com.rohan.productapp.presentation.ui.user

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rohan.productapp.R
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.User
import com.rohan.productapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val createUserButton = findViewById<Button>(R.id.createUserButton)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        // Observe loading state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.loadingState.collect { isLoading ->
                    progressBar.visibility = if (isLoading) ProgressBar.VISIBLE else ProgressBar.GONE
                }
            }
        }

        // Observe user state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.userState.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            resultTextView.text = "User created successfully: ${result.data.name}"
                        }

                        is ApiResult.Error -> {
                            resultTextView.text = "Error: ${result.error.message}"
                        }

                        ApiResult.NetworkError -> {
                            resultTextView.text = "Network Error"
                        }

                        null -> {
                            resultTextView.text = ""
                        }
                    }
                }
            }
        }

        // Handle button click
        createUserButton.setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(name = name, email = email)
                userViewModel.createUser(user)
            } else {
                resultTextView.text = "Please fill all fields"
            }
        }
    }
}