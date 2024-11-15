package com.my.sem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.widget.AdapterView;
import android.view.View;

public class ChatsActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private DrawerLayout _drawer;
	private String idprof = "";
	private double i = 0;
	private HashMap<String, Object> profile = new HashMap<>();
	private HashMap<String, Object> chatmap = new HashMap<>();
	private HashMap<String, Object> idchat = new HashMap<>();
	private HashMap<String, Object> workmessage = new HashMap<>();
	private String access = "";
	
	private ArrayList<HashMap<String, Object>> chatlist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> profilelist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> chats = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private ImageView addchat;
	private TextView textview1;
	private ListView listview1;
	private LinearLayout _drawer_linear1;
	private TextView _drawer_t1;
	private TextView _drawer_t2;
	private LinearLayout _drawer_linear3;
	private TextView _drawer_exit;
	
	private Intent view = new Intent();
	private DatabaseReference dbchats = _firebase.getReference("data/workchat");
	private ChildEventListener _dbchats_child_listener;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private DatabaseReference user = _firebase.getReference("data/profiles");
	private ChildEventListener _user_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chats);
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
		_drawer = (DrawerLayout) findViewById(R.id._drawer);ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(ChatsActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		addchat = (ImageView) findViewById(R.id.addchat);
		textview1 = (TextView) findViewById(R.id.textview1);
		listview1 = (ListView) findViewById(R.id.listview1);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_t1 = (TextView) _nav_view.findViewById(R.id.t1);
		_drawer_t2 = (TextView) _nav_view.findViewById(R.id.t2);
		_drawer_linear3 = (LinearLayout) _nav_view.findViewById(R.id.linear3);
		_drawer_exit = (TextView) _nav_view.findViewById(R.id.exit);
		auth = FirebaseAuth.getInstance();
		
		addchat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				view.setClass(getApplicationContext(), AdddialogActivity.class);
				startActivity(view);
				view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				finish();
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				view.setClass(getApplicationContext(), AdddialogActivity.class);
				view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(view);
				finish();
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				idchat = chats.get((int)_position);
				dbchats.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						chatlist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								chatlist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				dbchats.removeEventListener(_dbchats_child_listener);
				view.putExtra("idchat", idchat.get("id").toString());
				view.setClass(getApplicationContext(), ChatActivity.class);
				startActivity(view);
			}
		});
		
		_dbchats_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				idchat.put("id", _childKey);
				dbchats.child(_childKey).updateChildren(idchat);
				if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_childValue.get("staff").toString()) || FirebaseAuth.getInstance().getCurrentUser().getUid().equals(_childValue.get("observ").toString())) {
					chats.add(_childValue);
					listview1.setAdapter(new Listview1Adapter(chats));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
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
		dbchats.addChildEventListener(_dbchats_child_listener);
		
		_user_child_listener = new ChildEventListener() {
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
		user.addChildEventListener(_user_child_listener);
		
		_drawer_exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				view.setClass(getApplicationContext(), MainActivity.class);
				view.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(view);
				finish();
			}
		});
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		setTitle("Задания");
		profile = new HashMap<>();
		idprof = FirebaseAuth.getInstance().getCurrentUser().getUid();
		user.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				profilelist = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						profilelist.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				i = 0;
				for(int _repeat15 = 0; _repeat15 < (int)(profilelist.size()); _repeat15++) {
					if (idprof.equals(profilelist.get((int)i).get("id").toString())) {
						profile = profilelist.get((int)i);
						idprof = profile.get("sname").toString().concat(" ".concat(profile.get("name").toString().substring((int)(0), (int)(1)).concat(".".concat(profile.get("otch").toString().substring((int)(0), (int)(1)).concat(".")))));
						access = profile.get("access").toString();
					}
					i++;
				}
				if (access.equals("admin")) {
					linear2.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		dbchats.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				chats = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						chats.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				i = 0;
				for(int _repeat51 = 0; _repeat51 < (int)(chats.size()); _repeat51++) {
					if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chats.get((int)i).get("staff").toString()) || FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chats.get((int)i).get("observ").toString())) {
						i++;
					}
					else {
						chats.remove((int)(i));
					}
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
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
	public void onStart() {
		super.onStart();
		linear2.setVisibility(View.GONE);
		if (access.equals("admin")) {
			linear2.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onBackPressed() {
		
	}
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.chatlist, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _v.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _v.findViewById(R.id.textview2);
			
			textview1.setText(chats.get((int)_position).get("name").toString());
			textview2.setText("Задание на ".concat(chats.get((int)_position).get("datetime").toString()));
			
			return _v;
		}
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
