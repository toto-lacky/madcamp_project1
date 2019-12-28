package com.example.project1.ui.main

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.Addr_Profile
import com.example.project1.AddressAdapter
import com.example.project1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_address.*

/**
 * A placeholder fragment containing a simple view.
 */
class AddressFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    var addrList : ArrayList<Addr_Profile?>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    /* 기기의 연락처 데이터를 불러와서 AddressAdapter에 연결
    *  onCreateView 이후에 실행하기 위해 onStrat로 옮겨옴*/
    override fun onStart(){
        super.onStart()
        addrList = getContactList()
        val mAdapter = AddressAdapter(requireContext(), addrList)
        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(requireContext())
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_address, container, false)

        val fab: FloatingActionButton = root.findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): AddressFragment {
            return AddressFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    /* 기기로부터 연락처 목록을 가져오는 메소드 */
    fun getContactList(): ArrayList<Addr_Profile?>? {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.Contacts._ID
        )
        val selectionArgs: Array<String>? = null
        val sortOrder = (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC")
        val cursor: Cursor? = requireContext().contentResolver.query(
            uri, projection, null,
            selectionArgs, sortOrder
        )
        val hashlist =
            LinkedHashSet<Addr_Profile>()
        if (cursor?.moveToFirst() == true) {
            do {
                val photo_id = cursor.getLong(2)
                val newProfile =
                    //Addr_Profile(photo_id.toInt(), cursor.getString(1), cursor.getString(0))
                    Addr_Profile(R.drawable.def_icon, cursor.getString(1), cursor.getString(0))
                hashlist.add(newProfile)
            } while (cursor.moveToNext())
        }
        val contactItems: ArrayList<Addr_Profile?> = ArrayList(hashlist)
        for (i in contactItems.indices) {
            contactItems[i]?.id = i
        }
        return contactItems
    }
}
