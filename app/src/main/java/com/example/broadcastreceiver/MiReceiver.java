package com.example.broadcastreceiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MiReceiver extends BroadcastReceiver {
    //Tag de la aplicación
    private final String TAG = "MiReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //Detección y expulsión de intents
        //Constructor de strings
        StringBuilder sb = new StringBuilder();
        //Obtiene la acción y la muestra
        sb.append("Action = " + intent.getAction() + "\n");
        //Obtiene el valor uri
        sb.append("URI : "+ intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        //Genera un log con el valor string del StringBuilder
        String log = sb.toString();
        //Registra la información del intent
        Log.d(TAG,log);


        if (log.contains("true")){
            Toast.makeText(context,"Modo avión activado",Toast.LENGTH_SHORT).show();
        }else if (log.contains("false")){
            Toast.makeText(context,"Modo avión desactivado",Toast.LENGTH_SHORT).show();
        }else if (log.contains("LOW")){
            Toast.makeText(context,"Batería baja",Toast.LENGTH_SHORT).show();
        }



        //Envío de datos
        //Crea un objeto Intent
        Intent intentEnvio = new Intent();
        //Acción que le envía
        intentEnvio.setAction("com.example.broadcastreceiver.MiReceiver");
        //Incluye el nombre del mensaje que le va a enviar y el valor del mismo
        intentEnvio.putExtra("mensaje","Este es mi mensaje");

        //Método sendBroadcast
        context.sendBroadcast(intentEnvio);

        //Método para envío con permisos (en este caso un envío de SMS)
        context.sendOrderedBroadcast(intentEnvio, Manifest.permission.SEND_SMS);

        //Envío en Local donde obtiene el contexto y envía el mensaje
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentEnvio);




        //Recibimiento de datos
        //Recibe la información del intent y la almacena en una variable bundle
        Bundle extras = intent.getExtras();
        //Si tiene texto
        if (extras != null){
            if (String.valueOf(extras) == BatteryManager.EXTRA_BATTERY_LOW){
                String estado =  extras.getString(BatteryManager.EXTRA_BATTERY_LOW);
                Log.d("Batería",estado);
            }
            else if (String.valueOf(extras) == extras.getString(UserManager.DISALLOW_AIRPLANE_MODE)){
                String estado = extras.getString(UserManager.DISALLOW_AIRPLANE_MODE);
                Log.d("Modo Avión", estado);
            }


        }
    }


}

