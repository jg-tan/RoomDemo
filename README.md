# RoomDemo

Simple demonstration for MVVM architecture

Room
ViewModel
ViewModelFactory
DataBinding
RecyclerView
Event

MVVM Architecture

View -> ViewModel -> Repository -> LocalDataSource (Room)
                          '-> RemoteDataSource (Retrofit)

Components of RoomDatabase
Entity:
- Data class to represent objects stored in DB table
- use annotation: @Entity(tableName = "table_name")
- important annotations: @PrimaryKey(autoGenerate = true), @ColumnInfo

DAO: Data Access Object
- Interface that represents SQLite table and contains SQLite scripts
- important annotations: @Insert, @Update, @Delete, @Query
- should be a suspended function unless returning a LiveData<T> object

Database: Creates an instance of DAO
- abstract class, refer to SubscriberDatabase for boiler plate code

Components of MVVM
View

DataBinding

ViewModel

ViewModelFactory
- Used when viewModel needs to accept an initial state

Event
- Since viewmodel should know have dependency on view, use LiveData for handling events.
- Refer to boilerplate code for usage.

Repository
- contains methods for accessing the data layer
