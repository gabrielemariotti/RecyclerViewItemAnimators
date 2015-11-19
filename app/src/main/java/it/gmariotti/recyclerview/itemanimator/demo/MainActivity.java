package it.gmariotti.recyclerview.itemanimator.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import it.gmariotti.recyclerview.itemanimator.demo.ui.AnimationGridActivity;
import it.gmariotti.recyclerview.itemanimator.demo.ui.AnimationListActivity;
import it.gmariotti.recyclerview.itemanimator.demo.ui.GridActivity;
import it.gmariotti.recyclerview.itemanimator.demo.ui.ListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void linear(View viw){
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    public void grid(View viw){
        Intent intent = new Intent(this,GridActivity.class);
        startActivity(intent);
    }

    public void adapterList(View viw){
        Intent intent = new Intent(this, AnimationListActivity.class);
        startActivity(intent);
    }

    public void adapterGrid(View viw){
        Intent intent = new Intent(this, AnimationGridActivity.class);
        startActivity(intent);
    }

}
