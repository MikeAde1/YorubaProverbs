package example.yorubaproverbs.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import example.yorubaproverbs.domain.CategorizedListRepository
import example.yorubaproverbs.models.ProverbData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategorizedListRepositoryImpl@Inject constructor(
    firebaseDatabase: FirebaseDatabase
): CategorizedListRepository {
    private val databaseRef = firebaseDatabase.getReference("users")
    private val proverbDataList: MutableList<ProverbData> = mutableListOf()
    
    companion object {
        const val categoryKey = "Context"
    }
  
    override fun getProverbsByCategory(category: String) = callbackFlow {
        val eventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                proverbDataList.clear()
                dataSnapshot.children.forEach { snapshot ->
                    if (snapshot.child(categoryKey).value.toString().contains(category)) {
                        val proverbData = snapshot.getValue(
                            ProverbData::class.java
                        )
                        proverbData?.let { proverbDataList.add(it) }
                    }
                }
                trySend(proverbDataList.toList())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                databaseError.toException().printStackTrace()
                throw RuntimeException("An error just occurred")
            }
        }
        databaseRef.addValueEventListener(eventListener)

        awaitClose {
            databaseRef.removeEventListener(eventListener)
            close()
        }
    }
}