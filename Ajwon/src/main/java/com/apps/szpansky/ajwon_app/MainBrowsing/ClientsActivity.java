package com.apps.szpansky.ajwon_app.MainBrowsing;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.apps.szpansky.ajwon_app.ForPick.PickPerson;
import com.apps.szpansky.ajwon_app.R;
import com.apps.szpansky.ajwon_app.SimpleData.Client;
import com.apps.szpansky.ajwon_app.SimpleData.Order;
import com.apps.szpansky.ajwon_app.Tools.SimpleActivity;



public class ClientsActivity extends SimpleActivity {
    Bundle b = new Bundle();
    //Client client;

    //public static int clickedClientId = 1 ;


    public ClientsActivity() {
        super(new Client());
    }


    @Override
    protected ListView setListView() {
        return ((ListView) findViewById(R.id.list_view_simple_view));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //client = new Client(myDB);

        Button add = (Button) findViewById(R.id.add);
        add.setText("Pick Person");
        b = getIntent().getExtras();
        addData();
        listViewItemClick();
    }

    private void addData() {
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ClientsActivity.this, PickPerson.class);
                startActivityForResult(i, 1);

            }
        });
    }


    private void listViewItemClick() {

        final boolean[] flag = new boolean[1];
        flag[0] = true;

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                flag[0] = false;


                popupForDelete((int)id);

                //popupForDelete(id);
                //myDB.delete(Database.TABLE_ORDERS, Database.ORDER_CLIENT_ID, id);   //TODO delete in new activiti with informations


                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (flag[0]) {

                    Intent intent = new Intent(ClientsActivity.this, OrdersActivity.class);
                    toNextActivity.putInt("clickedClientId", (int)id);
                    intent.putExtras(toNextActivity);

                    Order.clickedClientId = (int) id;

                    ClientsActivity.this.startActivity(intent);
                    //clickedClientId = (int)id;
                }
                flag[0] = true;
            }
        });


    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                Integer personId = data.getIntExtra("personId" , 0);
                Integer catalogId = Client.clickedCatalogId;


                myDB.insertDataToClients(catalogId.toString(), personId.toString(), "Waiting");

                refreshListView();


            }
        }
    }


}
