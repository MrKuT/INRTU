package com.my.sem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.widget.AdapterView;
import java.text.DecimalFormat;

public class AdddialogActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private double i = 0;
	private HashMap<String, Object> profile = new HashMap<>();
	private String fio = "";
	private HashMap<String, Object> user = new HashMap<>();
	private HashMap<String, Object> work = new HashMap<>();
	private String date = "";
	
	private ArrayList<HashMap<String, Object>> profiles = new ArrayList<>();
	private ArrayList<String> users = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> addwork = new ArrayList<>();
	
	private EditText edittext1;
	private LinearLayout linear1;
	private TextView textview2;
	private CalendarView calendarview1;
	private Button setquest;
	private TextView textview1;
	private Spinner spinner1;
	
	private Calendar time = Calendar.getInstance();
	private Intent view = new Intent();
	private DatabaseReference dbwork = _firebase.getReference("data/workchat");
	private ChildEventListener _dbwork_child_listener;
	private DatabaseReference prof = _firebase.getReference("data/profiles");
	private ChildEventListener _prof_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.adddialog);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		edittext1 = (EditText) findViewById(R.id.edittext1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview2 = (TextView) findViewById(R.id.textview2);
		calendarview1 = (CalendarView) findViewById(R.id.calendarview1);
		setquest = (Button) findViewById(R.id.setquest);
		textview1 = (TextView) findViewById(R.id.textview1);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		calendarview1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView _param1, int _param2, int _param3, int _param4) {
				final int _year = _param2;
				final int _month = _param3;
				final int _day = _param4;
				date = String.valueOf(_day).concat(".".concat(String.valueOf(_month).concat(".".concat(String.valueOf(_year)))));
			}
		});
		
		setquest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				work = new HashMap<>();
				work.put("staff", user.get("id").toString());
				work.put("datetime", date);
				work.put("name", edittext1.getText().toString());
				work.put("observ", profile.get("id").toString());
				dbwork.push().updateChildren(work);
				view.setClass(getApplicationContext(), ChatsActivity.class);
				view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(view);
				finish();
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				user = profiles.get((int)_position);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		_dbwork_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		dbwork.addChildEventListener(_dbwork_child_listener);
		
		_prof_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		prof.addChildEventListener(_prof_child_listener);
	}
	private void initializeLogic() {
		setTitle("Добавление задания");
		prof.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				profiles = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						profiles.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				i = 0;
				for(int _repeat12 = 0; _repeat12 < (int)(profiles.size()); _repeat12++) {
					profile = profiles.get((int)i);
					fio = profile.get("sname").toString().concat(" ".concat(profile.get("name").toString().substring((int)(0), (int)(1)).concat(".".concat(profile.get("otch").toString().substring((int)(0), (int)(1)).concat(".")))));
					if (profile.get("access").toString().equals("user")) {
						users.add(fio);
					}
					i++;
				}
				spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, users));
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		dbwork.addChildEventListener(_dbwork_child_listener);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		view.setClass(getApplicationContext(), ChatsActivity.class);
		view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(view);
		finish();
	}
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
