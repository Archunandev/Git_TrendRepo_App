# Git_TrendRepo_App
Github Trending Repositories

## Package Structure 📦
    com.example.gittrendrepoapp # Root Package
    |___api
    |   |
    |   |__RepoApi
    |   |__RetrofitInstance
    |
    |___db
    |   |__Dao
    |   |__Database
    |
    |___model
    |   |__Response
    |
    |___repository
    |   |__Repository
    |
    |___ui
    |   |__activity
    |   |  |___MainActivity
    |   |__Adapter
    |      |__ListAdapter
    |
    |___util
    |   |__App
    |   |__util
    |   |__Resource
    |   |__Constants
    |
    |___view model
        |__ViewModelFactory
        |__ViewModel

## Architecture 🗼
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

## Setting Up 🌞
Setting up the status bar, menu search, swipe to refresh & view binding

## Design Ui 🎨
Add recyclerview & this items design.

## Library Setup 🛠
Added the required library for remote access & room database.

## ViewModel & Retrofit 🧰
Using retrofit, live data & view model to make our api call and code structure perfect.

## Adapter & Error Handling 🧰
Use binding adapter, handle network error & loading state.

## Adapter Filter Search 🧰
Implemented a adapter filter search.

## Setting Up Room 🧰
Implemented the room database & check internet connection for working with offline support.

## Offline Support 🧰
save the list on room database.Check user internet connection to show a list from db or server.

## Thanks & Hopefully 🤓
:)