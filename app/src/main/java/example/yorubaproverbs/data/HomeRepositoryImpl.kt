package example.yorubaproverbs.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import example.yorubaproverbs.domain.HomeRepository
import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.RuntimeException
import javax.inject.Inject

class HomeRepositoryImpl@Inject constructor(
    firebaseDatabase: FirebaseDatabase
): HomeRepository {
    private val searchResult: MutableList<ProverbData> = mutableListOf()
    private val docRef = firebaseDatabase.getReference("users")

    override fun performSearch(searchText: String, searchLanguage: String): Flow<List<ProverbData>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    searchResult.clear()
                    Log.d("search:", " showing")
                    for (dataSnapshot in snapshot.children) {
                        val context = if (searchLanguage == "English") {
                            (dataSnapshot.child("Translation").value).toString()
                        } else {
                            (dataSnapshot.child("Content").value).toString()
                        }
                        //might use ignore case in this scenario
                        if (context.lowercase().contains(searchText.lowercase())) {
                            val proverbData = dataSnapshot.getValue(ProverbData::class.java)
                            proverbData?.let { proverb -> searchResult.add(proverb) }
                        }
                    }
                    trySend(searchResult.toList())
                }

                override fun onCancelled(error: DatabaseError) {
                    throw RuntimeException("An error just occurred.")
                }
            }
            docRef.addListenerForSingleValueEvent(listener)

            awaitClose {
                docRef.removeEventListener(listener)
                // Remove the database listener
                close()
            }
        }
}