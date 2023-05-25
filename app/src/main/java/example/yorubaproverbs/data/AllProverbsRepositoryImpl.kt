package example.yorubaproverbs.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import example.yorubaproverbs.models.ProverbData
import example.yorubaproverbs.presentation.dashboard.all.AllProverbsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllProverbsRepositoryImpl @Inject constructor(
    firebaseDataBase: FirebaseDatabase
) : AllProverbsRepository {

    private val databaseRef = firebaseDataBase.getReference("users")
    private val proverbDataList: MutableList<ProverbData> = mutableListOf()
    override fun getAllProverbs(): Flow<List<ProverbData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                proverbDataList.clear()
                dataSnapshot.children.forEach { snapshot ->
                    val proverbData = snapshot.getValue(
                        ProverbData::class.java
                    )
                    proverbData?.let {
                        proverbDataList.add(it)
                    }
                }
                trySend(proverbDataList.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                databaseError.toException().printStackTrace()
                throw RuntimeException("An error just occurred")
            }
        }
        databaseRef.addValueEventListener(listener)

        awaitClose {
            databaseRef.removeEventListener(listener)
            close()
        }
    }
}