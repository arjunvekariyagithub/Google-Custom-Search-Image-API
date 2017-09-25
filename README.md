# Smule Android challenge

## Libraries
  - [Retrofit](https://square.github.io/retrofit/) as HTTP client
  - [Glide](https://github.com/bumptech/glide) for image downloaiding and display
  - [RxJava](https://github.com/ReactiveX/RxJava) to efficiently handle async network calls and employ reactive pattern
  - [Dagger](https://google.github.io/dagger/) for dependency injection
  - [Databinding](https://developer.android.com/topic/libraries/data-binding/index.html)
 
## Architecture
  - [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (Mode-View-ViewModel)

### Testing
  - Basic JUnit tests are implemented.

### Pagination
  - I have used [EndlessRecyclerViewScrollListener](https://gist.github.com/nesquena/d09dc68ff07e845cc622) for pagination as
    it is robust and very simple to use
    
### Why I choose Glide amonst many available options?
I have experimented with other Image loading libraries: [Picasso](http://square.github.io/picasso/), [Volly](https://github.com/google/volley) and [Fresco](https://github.com/facebook/fresco), but [Glide](https://github.com/bumptech/glide) seems to be performing best amongst all due to it's well optimized Cache mechanism.
