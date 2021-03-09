package petras.bukelis.balticamadeusandroidtask.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import petras.bukelis.balticamadeusandroidtask.db.AppDao;
import petras.bukelis.balticamadeusandroidtask.db.AppDatabase;
import petras.bukelis.balticamadeusandroidtask.entities.Post;
import petras.bukelis.balticamadeusandroidtask.entities.User;

public class DBRepository {
    private AppDao appDao;
    private LiveData<List<Post>> allPosts;
    private LiveData<List<User>> allUsers;

    public DBRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);
        appDao = database.appDao();
        allPosts = appDao.getAllPosts();
        allUsers = appDao.getAllUsers();
    }

    //Post DB
    public void insert(Post post)
    {
        new InsertPostAsyncTask(appDao).execute(post);
    }
    public void update(Post post)
    {
        new UpdatePostAsyncTask(appDao).execute(post);
    }
    public void delete(Post post)
    {
        new DeletePostAsyncTask(appDao).execute(post);
    }
    public LiveData<List<Post>> getAllPosts() {
        return allPosts;
    }

    private static class InsertPostAsyncTask extends AsyncTask<Post,Void,Void>
    {
        private AppDao appDao;

        private InsertPostAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            appDao.insertPost(posts[0]);
            return null;
        }
    }

    private static class UpdatePostAsyncTask extends AsyncTask<Post,Void,Void>
    {
        private AppDao appDao;

        private UpdatePostAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            appDao.updatePost(posts[0]);
            return null;
        }
    }
    private static class DeletePostAsyncTask extends AsyncTask<Post,Void,Void>
    {
        private AppDao appDao;

        private DeletePostAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(Post... posts) {
            appDao.deletePost(posts[0]);
            return null;
        }
    }
    //User DB
    public void insert(User user)
    {
        new InsertUserAsyncTask(appDao).execute(user);
    }
    public void update(User user)
    {
        new UpdateUserAsyncTask(appDao).execute(user);
    }
    public void delete(User user)
    {
        new DeleteUserAsyncTask(appDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User,Void,Void>
    {
        private AppDao appDao;

        private InsertUserAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            appDao.insertUser(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<User,Void,Void>
    {
        private AppDao appDao;

        private UpdateUserAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            appDao.updateUser(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User,Void,Void>
    {
        private AppDao appDao;

        private DeleteUserAsyncTask(AppDao appDao)
        {
            this.appDao = appDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            appDao.deleteUser(users[0]);
            return null;
        }
    }
}
