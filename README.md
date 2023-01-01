<p align="left">
  <a href="#"><img alt="Android OS" src="https://img.shields.io/badge/OS-Android-3DDC84?style=flat-square&logo=android"></a>
  <a href="#"><img alt="Languages-Kotlin" src="https://flat.badgen.net/badge/Language/Kotlin?icon=https://raw.githubusercontent.com/binaryshrey/Awesome-Android-Open-Source-Projects/master/assets/Kotlin_Logo_icon_white.svg&color=f18e33"/></a>
  <a href="#"><img alt="PRs" src="https://img.shields.io/badge/PRs-Welcome-3DDC84?style=flat-square"></a>
</p>

<p align="left">
:eyeglasses: The Weather contributor, Lukoh.
</p><br>

# Weather

Weather 앱은 [AdvancedAppArchitecture](https://github.com/Lukoh/AdvancedAppArchitecture) 및 [LukohSplash](https://github.com/Lukoh/LukohSplash) 및 Android 최신 아키텍처 구성 요소인 Jetpack을 기반으로 하며 MVVM 디자인 패턴을 따르고 있습니다. 또한 Weather 앱 아키텍처는 프리젠테이션 계층, 도메인 계층 및 리포지토리 계층으로 구성되어 있습니다. 그리고 Weather App에는 Advanced Android App Architecture로 새로운 Android App 개발을 위한 최신 기술이 적용되었습니다.. 이러한 새로운 기술들이 안드로이드 앱이 확장되어 더욱 경쟁력 있고 일관성을 유지하도록 도와줍니다. 또한 Weather App 의 모든 모듈에 Kotlin 언어를 적용하고 있으며 대부분의 코드가 Kotlin으로 작성되어 있습니다.

약 3년 전부터 LiveData와 RxJava, RxKotlin을 사용하지 않고 Android 아키텍처 구성 요소와 Kotlin Coroutine 및 Flow를 사용하여 고성능 앱을 개발할 수 있다고 생각하여 AdvancedAppArchitecture 및 LukohSplash 를 개발하게 되었습니다.

아래 내용들은 제가 제 [Mediem](https://medium.com/@lukohnam) 에 올린 좋은 아키텍쳐 기반에서 앱을 개발 하는 방법들에 관한 기술글들입니다. 약 3년전 개발된 AppArchitecture  를 기반으로 LukohSplash 를 개발하였고, Weather 앱은 LukohSplash 를 좀더 개선한 AdvancedAppArchitecture 를 기반으로 개발되었습니다. 제 기술 스택을 참고하시려면 [LukohSplahs](https://github.com/Lukoh/LukohSplash) 와 [AdvancedAppArchitecture](https://github.com/Lukoh/AdvancedAppArchitecture) 를 참고해 주십시요.

아래 내용은 제가 [Medium](https://medium.com/@lukohnam) 에 올린 기술관련 글들과 관련 된 내용입니다. 혹시 관심을 가지고 있으시면 방문하셔서 봐주시면 감사드리겠습니다.

Here are the effective ways to focus on your business logic & simplify modules & reduce the boiler-plate code & using the Module-Pattern(Called Module Pattern by Lukoh). A well-designed/implemented Mobile App using Module-Pattern(that is, well-designed App Architecture) can be extended to new apps faster and easier by applying/adapting new UI layouts and business logic. It helps you focus your business logic and create better services, and you can quickly deliver user-friendly features to your users.

![alt App Architecture](https://github.com/Lukoh/AdvancedAppArchitecture/blob/main/flow.png) 

Please refer to [LukohSplash](https://github.com/Lukoh/LukohSplash) & [Better Android Apps Using latest advanced Architecture](https://medium.com/oheadline/better-android-apps-using-mvvm-with-clean-architecture-2cc49e68f41d) & [How to focus effective business logic & implement more expandable & simplify modules & reduce the boiler-plate code using the Module-Pattern to make Android App.](https://medium.com/@lukohnam/how-to-focus-effective-business-logic-implement-more-expandable-simplify-modules-reduce-the-81ae1af23e4e)

Please refer to [How to share/communicate events (independent data) across app components such as Activities, Fragments, Services, etc using ViewModel & SharedFlow.](https://medium.com/@lukohnam/how-to-share-communicate-events-independent-data-across-app-components-such-as-activities-353c96e32775)

Typically, the UI layer contains UI-related state and UI business logic. UI business logic is what gives your app value and the role of the UI is to display the application data on the screen and also to serve as the primary point of user interaction. Separating UI business logic from UI simplifies relationships and allows business logic to be reproduced outside of the UI for unit-testing.

Please refer to [How to handle your internal business logic using ViewModel and UseCase to decouple the logic from UI module.](https://medium.com/@lukohnam/how-to-handle-your-internal-business-logic-using-viewmodel-and-usecase-to-decouple-the-logic-from-f20ee9f7e4a5)

Now let’s dive into my open-source project, AdvancedAppArchitecture, which is based on LukohSplash & the Android MVVM with Clean Architecture and the latest libraries like Jetpack.
And I'm learning Jetpack Compose and will apply it to AdvancedAppArchitecture.

I'm happy to introdce the aritle, "Adding a domain layer", to add a Domain Layer. D It's very usefult to make Domain Layer in your project. [Adding a domain layer](https://medium.com/@donturner/adding-a-domain-layer-bc5a708a96da)

제게 이메일 주소는 아래와 같습니다.

lukoh.nam@gmail.com
