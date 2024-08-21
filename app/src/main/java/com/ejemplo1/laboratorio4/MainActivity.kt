package com.ejemplo1.laboratorio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthyLivingApp()
        }
    }
}


@Composable
fun HealthyLivingApp() {
    var nameInput by remember { mutableStateOf(TextFieldValue("")) }
    var urlInput by remember { mutableStateOf(TextFieldValue("")) }
    val itemList = remember { mutableStateListOf<Pair<String, String>>() }

    Column(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (nameInput.text.isEmpty()) {
                    Text(text = "Enter Recipe Name")
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = urlInput,
            onValueChange = { urlInput = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (urlInput.text.isEmpty()) {
                    Text(text = "Enter Image URL")
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (nameInput.text.isNotEmpty() && urlInput.text.isNotEmpty()) {
                itemList.add(Pair(nameInput.text, urlInput.text))
                nameInput = TextFieldValue("")  // Clear input fields after adding item
                urlInput = TextFieldValue("")
            }
        }) {
            Text(text = "Add")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(itemList.size) { index ->
                val item = itemList[index]
                RecipeCard(name = item.first, imageUrl = item.second)
            }
        }
    }
}

@Composable
fun RecipeCard(name: String, imageUrl: String) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)) {
        Row(modifier = Modifier.padding(20.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = name)
        }
    }
}