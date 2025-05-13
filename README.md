# Moodvies
Moodvies is a movie recommendation app designed to help users discover films based on their genre preferences, trailers, and reviews. Whether you're unsure what to watch or looking for something that fits your current mood, Moodvies makes the process easier, faster, and more personalized.

## Architecture
The project follows the MVVM (Model-View-ViewModel) design pattern with a Clean Architecture approach. This architecture promotes separation of concerns and provides a scalable and maintainable codebase. Here's an overview of the project's architecture:

- **Presentation Layer**: Contains the UI components, including activities and fragments. It interacts with the ViewModel layer to retrieve and display data to the user.

- **Domain Layer**: Contains the business logic of the application. It defines the various use cases and orchestrates the interactions between the data layer and the presentation layer.

- **Data Layer**: Contains the repositories and data sources responsible for retrieving and persisting data. It abstracts the underlying data storage implementation and provides a clean interface for the Use Case layer.

## Tech Stack

- **Kotlin** for Android development
- **MVVM Architecture**
- **Hilt** for Dependency Injection
- **Retrofit** for API integration
- **Paging 3** for efficient data loading
- **Glide** for image loading
- **YouTube Player** for trailer playback
- **Material Design 3** for modern UI/UX components

## Getting Started
To build and run the project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/rahmadnoorikhsan/Moodvies.git`
2. Open the project in Android Studio.
3. Ensure that the required SDK versions and dependencies are installed.
4. Add this code to your `local.properties` file.
```
BASE_URL=https\://api.themoviedb.org/3/
BASE_URL_IMAGE=https\://image.tmdb.org/t/p/original
API_KEY=(Your TMDB API Key)
```
5. Build and run the app on an emulator or physical device.

## Preview
<table>
    <tr>
        <td><img src="https://raw.githubusercontent.com/rahmadnoorikhsan/Moodvies/refs/heads/master/screenshots/home.jpg" align="center" alt="4"</td>
        <td><img src="https://raw.githubusercontent.com/rahmadnoorikhsan/Moodvies/refs/heads/master/screenshots/detail.jpg" align="center" alt="4"</td>
        <td><img src="https://raw.githubusercontent.com/rahmadnoorikhsan/Moodvies/refs/heads/master/screenshots/trailer.jpg" align="center" alt="4"</td>
        <td><img src="https://raw.githubusercontent.com/rahmadnoorikhsan/Moodvies/refs/heads/master/screenshots/reviews.jpg" align="center" alt="4"</td>
    </tr>
<table>
