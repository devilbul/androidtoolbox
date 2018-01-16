package fr.isen.galiay.androidtoolbox.bdd;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id_user IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);

    @Insert
    void insertBothUsers(User user1, User user2);

    @Update
    void updateUsers(User... users);
}
