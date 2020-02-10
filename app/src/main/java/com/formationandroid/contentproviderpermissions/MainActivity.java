package com.formationandroid.contentproviderpermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity
{
	
	// Constantes :
	private static final int PERMISSION_CONTACTS = 1234;
	
	// Vues :
	private TextView textViewContacts = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// vues :
		textViewContacts = findViewById(R.id.contacts);
		
		// demande d'autorisation, si ce n'est déjà fait :
		int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
		if (permission != PackageManager.PERMISSION_GRANTED)
		{
			// affichage de la popup demande de permission :
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONTACTS);
		}
		else
		{
			// permission déjà accordée, on peut afficher les contacts sans risque :
			afficherContacts();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if (requestCode == PERMISSION_CONTACTS)
		{
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				// permission accordée, on peut afficher les contacts sans risque :
				afficherContacts();
			}
			else
			{
				// permission refusée :
				Toast.makeText(this, "Permission contacts refusée...", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	 * Affichage des contacts.
	 */
	private void afficherContacts()
	{
		// init :
		List<Contact> listeContacts = new ArrayList<>();
		
		// requète :
		Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				null,
				null,
				null,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
		
		// parcours du curseur :
		if (cursor != null)
		{
			try
			{
				while (cursor.moveToNext())
				{
					Contact contact = new Contact(
							cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
							cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					listeContacts.add(contact);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				cursor.close();
			}
		}
		
		// affichage de la liste de contacts :
		StringBuilder stringBuilderContacts = new StringBuilder();
		for (Contact contact : listeContacts)
		{
			stringBuilderContacts.append(contact.getNom()).append(" : ").append(contact.getNumero()).append("\n");
		}
		textViewContacts.setText(stringBuilderContacts.toString());
	}
	
}
