package kr.co.ajsoft.ex38datastorageinternal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

    }

    public void clickSave(View view) {

        String data=et.getText().toString();
        et.setText("");

        //읽어온 문자열 data를 내부메모리(Internal Storage)에 저장
        //file에 저장할 수 있도록 Stream을 생성해서 데이터 저장
        //안드로이드에서는 file과 연결하는 Stream을 열어주는 기능을
        //Activity가 이미 메소드로 보유하고 있음.
        try {
            FileOutputStream fileOutputStream=openFileOutput("Data.txt",MODE_APPEND);
            //위 바이트스트림(fileOutPutStream)을
            //문자 스트림(Writer)으로 변환
            PrintWriter writer=new PrintWriter(fileOutputStream);

            writer.println(data);
            writer.flush();

            writer.close();

            Toast.makeText(this,"SAVED",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void clickLoad(View view) {
        try {
            FileInputStream fileInputStream=openFileInput("Data.txt");
            //바이트스트림 -> 문자스트림으로 변환
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream);
            //문자스트림->보조스트림으로 변환
            BufferedReader reader=new BufferedReader(inputStreamReader);

            StringBuffer buffer=new StringBuffer(); //String 쌓아놓는 친구
            String line=reader.readLine();
            while(true){
                buffer.append(line+"\n"); //buffer는 줄바꿈 빼고 가져옴 so,줄띄움 추가
                line=reader.readLine();
                if(line==null) break;

            }

            tv.setText(buffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
