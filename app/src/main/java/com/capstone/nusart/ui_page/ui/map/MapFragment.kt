package  com.capstone.nusart.ui_page.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.nusart.R
import com.capstone.nusart.ViewModelFactory
import com.capstone.nusart.data.api.response.ListArt
import com.capstone.nusart.databinding.FragmentMapBinding
import com.capstone.nusart.preference_manager.UserManager
import com.capstone.nusart.ui_page.main.MainActivity
import com.capstone.nusart.data.ResultResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null

    private val binding get() = _binding!!
    private lateinit var preferences: UserManager
    private lateinit var mMap: GoogleMap
    private lateinit var factory: ViewModelFactory
    private val viewModel: MapViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //setupView()
        setupProperty()
        setupAction()
        //getStoryMap()

        return root
    }

    /*private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }*/

    private fun setupProperty() {
        factory = ViewModelFactory.getInstance(requireActivity())
        preferences = UserManager(requireActivity())
    }

    /*
    private fun getStoryMap() {
        val token = "Bearer ${preferences.getToken()}"
        viewModel.getWithLocation(1, token).observe(requireActivity()) { response ->
            when (response) {
                is ResultResource.Loading -> {
                    Log.e(TAG, "Loading..")
                }
                is ResultResource.Success -> {
                    showMarker(response.data.listStory)
                }
                is ResultResource.Error -> {
                }
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment)
        if (mapFragment is SupportMapFragment) {
            mapFragment.getMapAsync(this)
        } else {
            Log.e(TAG, "Fragment with ID R.id.ini_fragment_map is not a SupportMapFragment")
        }
    }



    private fun showMarker(listStory: List<ListArt>) {
        for (story in listStory) {
            val latLng = LatLng(story.lat, story.lon)
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .snippet(story.description)
                    .title(story.name)
            )
        }
    }
    */
    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /*
    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }*/

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                enableMyLocation()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        configureMap()
    }

    private fun configureMap() {
        mMap.uiSettings.isZoomControlsEnabled = false
        enableMyLocation()
       // setMapStyle()

        // Find your buttons in the binding
        val btnZoomIn = binding.root.findViewById<Button>(R.id.btnZoomIn)
        val btnZoomOut = binding.root.findViewById<Button>(R.id.btnZoomOut)


        // Set click listeners for zoom in and zoom out
        btnZoomIn.setOnClickListener { zoomIn() }
        btnZoomOut.setOnClickListener { zoomOut() }

        // Set up map type buttons
    }

    private fun zoomIn() {
        mMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    private fun zoomOut() {
        mMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun changeMapType(mapType: Int) {
        mMap.mapType = mapType
    }

    companion object {
        private val TAG = MapFragment::class.simpleName
    }

}