package com.example.user.pdfviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Label;

import java.io.File;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String spinnerSelect;
    EditText mName;
    EditText mRoll;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         spinner = (Spinner) findViewById(R.id.spinner);
         mName=(EditText) findViewById(R.id.name);
            mRoll=(EditText)findViewById(R.id.roll_no);
            b1=(Button) findViewById(R.id.button);




        final String name = mName.getText().toString();
        final String roll= mRoll.getText().toString();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.department_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf(name,roll,spinnerSelect);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelect=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public  void generatePdf(String name,String roll,String spinnerSelect){

        String FILE = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/"+name+".pdf";
        Document objDocument = new Document();

        objDocument.setCreator("khushboo");
        objDocument.setAuthor("khushboo");
        objDocument.setTitle("details Page");

        Page objPage = new Page(PageSize.LETTER, PageOrientation.PORTRAIT,
                54.0f);

        Label objLabel = new Label(name, 0, 0, 504, 100,
                Font.getHelvetica(), 18, TextAlign.CENTER);
        Label objLabel2 = new Label(roll, 0, 0, 504, 100,
                Font.getHelvetica(), 18, TextAlign.CENTER);
        Label objLabel3 = new Label(spinnerSelect, 0, 0, 504, 100,
                Font.getHelvetica(), 18, TextAlign.CENTER);

        objPage.getElements().add(objLabel);
        objPage.getElements().add(objLabel2);
        objPage.getElements().add(objLabel3);


        objDocument.getPages().add(objPage);

        try {
            // Outputs the document to file
            objDocument.draw(FILE);
            Toast.makeText(this, "File has been written to :" + FILE,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Error, unable to write to file\n" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        //to open the pdf in an pdf viewer app.(eg Adobe Acrobat pdf viewer)
        File file = new File(FILE);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

}
