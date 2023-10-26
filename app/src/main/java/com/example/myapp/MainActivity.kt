package com.example.myapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(name: String) {
    var start = remember {
        mutableStateListOf<Boolean>(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    }
    var time = remember {
        mutableStateListOf<Int>(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
    }

val list = remember {
    mutableStateListOf<String>()

}
    // "SAA" , "TMOD","GE","Speaker1","Evaluator1","Speaker2","Evaluator2","TTM","Timer","WMG","Ah-Counter")
    var stop = remember { mutableStateListOf<Boolean>() }

    var mm = remember {
        mutableStateListOf<Int>()
    }
    var ss = remember {
        mutableStateListOf<Int>()
    }
    val context = LocalContext.current
    val activity = context as Activity
    val context1 = LocalContext.current
    val activity1 = context1 as Activity

    Column(modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally ) {
        Spacer(modifier = Modifier.height(10.dp))
        var inputval = remember { mutableStateOf(TextFieldValue()) }
Row() {
    TextField(value = inputval.value, onValueChange = { inputval.value = it } ,  modifier = Modifier.weight(0.8f),
        placeholder = { Text(text = "Enter RoleName(Name of Role Player)") },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
        ),
        textStyle = androidx.compose.ui.text.TextStyle(
            color = Color.Black, fontSize = TextUnit.Unspecified,
            fontFamily = FontFamily.SansSerif
        ),
        maxLines = 1,
        singleLine = true
    )
    Button(onClick = {

        if(inputval.value.text.indexOf("(") != -1  && inputval.value.text.indexOf(")") != -1){
        list.add(inputval.value.text);

        stop.add(false);
        mm.add(0);
        ss.add(0);
        if(start.size >20) {
            start.add(false);
            time.add(0);
        }
        }
        else{

           Toast.makeText(context1, "Invalid Naming Convention", Toast.LENGTH_LONG).show()
        }


                     },modifier = Modifier
        .weight(0.2f)
        .height(55.dp)
    ) {
        Text(text = "ADD")
    }

}

        Spacer(modifier = Modifier.height(Dp(1f)))


        LazyColumn() {
            itemsIndexed(list) { index, item ->

                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        backgroundColor = Color.White,
                        elevation = 35.dp,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .width(110.dp)
                            .combinedClickable
                                (
                                onClick = {

                                    if (!start[index]) {
                                        start[index] = true
                                        stop[index] = true
                                    } else if (stop[index]) {
                                        start[index] = false
                                        stop[index] = false
                                    }
                                }
                            )



                    ) {
                        Column() {
                            Row(

                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = list[index],
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(start = 10.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                var c = time[index]
                                mm[index] = (c % 3600) / 60
                                ss[index] = c % 60
                                var a = mm[index]
                                var b = ss[index]
                                LaunchedEffect(key1 = start[index], key2 = time[index]) {
                                    if (start[index]) {
                                        delay(1000L)
                                        time[index] += 1

                                    }
                                }




                                Text(
                                    text = "%02d:%02d".format(a, b),

                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.End)
                                        .padding(end = 12.dp)
                                )

                            }
                            
                            Row(
                                modifier  = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Button(onClick = {
                                        list.removeAt(index)
                                    time.removeAt(index)
                                    mm.removeAt(index)
                                    ss.removeAt(index)
                                    stop.removeAt(index)
                                }, modifier = Modifier
                                    .wrapContentWidth(Alignment.Start)
                                    .padding(start = 3.dp) , shape = RoundedCornerShape(10.dp) ) {
                                    Text(text = "DELETE")
                                }
                                Spacer(modifier = Modifier.width(30.dp))
                                Button(onClick = {

                                    time[index] = 0
                                    mm[index] = 0
                                    ss[index] = 0
                                    start[index] = false
                                    stop[index] = false

                                }, modifier =  Modifier.wrapContentWidth(Alignment.End) ,shape = RoundedCornerShape(10.dp)) {
                                    Text(text="RESET")
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
        Button(onClick = {

            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 23
            )

            // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
            // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
            val folder: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            // Storing the data in file with name as geeksData.txt
            val file = File(folder, "TimerData.csv")
            val n = list.size-1
            var str:String  = ""

                for (i in 0..n) {
                    if(i==0)
                        str += "Name of the Role" + "," + "Name of Role Player" + "," + "TIme(mm:ss)" +"\n"

                    var str1 = list[i].subSequence(0, list[i].indexOf('('))
                    var str2 = list[i].subSequence( list[i].indexOf('(')+1, list[i].indexOf(')'))
                    str += "$str1 , $str2" + "," + (time[i] % 3600) / 60 + ":" + time[i] % 60 +"\n"
                }
                writeTextData(file, str, context)


            // displaying a toast message
            Toast.makeText(context, "Saved in Downloads", Toast.LENGTH_LONG).show()


        }
        ,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("SAVE AS FILE" , fontWeight = FontWeight.Bold , fontFamily = FontFamily.SansSerif
            )
        }
    }

//
//    Column(modifier = Modifier.fillMaxSize()) {
//        Card(
//            backgroundColor = Color.Whit`e, shape = RoundedCornerShape(20.dp), modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .width(200.dp)
//                .clickable {
//                    if (!start[0]) {
//                        start[0] = true
//                        stop[0] = true
//                    } else if (stop[0]) {
//                        start[0] = false
//                        stop[0] = false
//                    }
//                }
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(text = list[0], fontSize = 30.sp )
//                var c = time[0]
//                mm[0] = (c % 3600) / 60
//                ss[0] = c%60
//                var a = mm[0]
//                var b = ss[0]
//
//                Text(text = "%02d:%02d".format(a,b), modifier = Modifier.padding(start = 20.dp), fontSize = 30.sp , fontWeight = FontWeight.Bold)
//
//            }
//
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Card(
//            backgroundColor = Color.White, shape = RoundedCornerShape(20.dp), modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .width(200.dp)
//                .clickable {
//                    if (!start[1]) {
//                        start[1] = true
//                        stop[1] = true
//                    } else if (stop[1]) {
//                        start[1] = false
//                        stop[1] = false
//                    }
//
//                },
//            elevation = 30.dp
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(text = list[1], fontSize = 30.sp)
//                var c = time[1]
//                mm[1] = (c % 3600) / 60
//                ss[1] = c%60
//                var a = mm[1]
//                var b = ss[1]
//                Text(text = "%02d:%02d".format(a,b), modifier = Modifier.padding(start = 20.dp), fontSize = 30.sp , fontWeight = FontWeight.Bold)
//            }
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Card(
//            backgroundColor = Color.White, shape = RoundedCornerShape(20.dp), modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .width(200.dp)
//                .clickable {
//                    if (!start[2]) {
//                        start[2] = true
//                        stop[2] = true
//                    }
//                    else if(stop[2]) {
//                        start[2] = false
//                        stop[2] = false
//                    }},
//            elevation = 30.dp
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(text = list[2], fontSize = 30.sp)
//                var c = time[2]
//                mm[2] = (c % 3600) / 60
//                ss[2] = c%60
//                var a = mm[2]
//                var b = ss[2]
//                Text(text = "%02d:%02d".format(a,b), modifier = Modifier.padding(start = 20.dp), fontSize = 30.sp , fontWeight = FontWeight.Bold)
//            }
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Card(
//            backgroundColor = Color.White, shape = RoundedCornerShape(20.dp), modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .width(200.dp)
//                .clickable {
//                    if (!start[3]) {
//                        start[3] = true
//                        stop[3] = true
//                    }
//                    else if(stop[3]) {
//                        start[3] = false
//                        stop[3] = false
//                    } },
//            elevation = 30.dp
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(text = list[3], fontSize = 30.sp)
//                var c = time[3]
//                mm[3] = (c % 3600) / 60
//                ss[3] = c%60
//                var a = mm[3]
//                var b = ss[3]
//                Text(text = "%02d:%02d".format(a,b), modifier = Modifier.padding(start = 20.dp), fontSize = 30.sp , fontWeight = FontWeight.Bold)
//            }
//        }
//    }
/*


    */
}

private fun writeTextData(file: File, data: String, context: Context) {
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(data.toByteArray())

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun DeefaultPreview() {

   
Surface(color = Color.White) {


    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .height(20.dp)
            .width(60.dp)
    ) {
        Text(text = "Save as file" , color = Color.White , fontWeight = FontWeight.Bold  )
    }
}
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyAppTheme {
        DeefaultPreview()
    }
}