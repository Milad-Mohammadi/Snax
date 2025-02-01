![banner](https://github.com/user-attachments/assets/01329c82-8f4a-445c-b616-ed5df11f46ee)
Snax is a powerful and customizable Android snack bar library built with Jetpack Compose. It offers sleek animations, swipe-to-dismiss functionality, and a variety of styling options to enhance the user experience.

### 🚀 Features
- 🔥 Beautiful and customizable snack bars
- 🎨 Custom themes, colors, and icons
- 🏗️ Easy-to-use API for quick integration
- ✨ Smooth animations with enter/exit transitions
- 📦 Supports actions, dismiss behaviors, and progress indicators

### Demo
| Action | Title | Dissmisable | Progress | Animation | Shape | Types |
|---|---|---|---|---|---|---|
| X | X | X | X | X | X | X |

‌‌
‌‌
‌‌
## 📦 Installation
To integrate Snax into your project, add the following dependency to your `build.gradle` (Module-level):
```kts
repositories {
    mavenCentral()
}

dependencies {
  implementation("com.vimilad:Snax:1.0.1")
}
```

‌‌
‌‌
‌‌
## 🛠 Usage
### Initialize Snax State
```kt
val snaxState = rememberSnaxState()
```
### Display a Simple Snack Bar
```kt
snaxState.setData(
    type = SnaxType.SUCCESS,
    message = "Your action was successful!"
)
```
### Display a Snack Bar with Action
```kt
snaxState.setData(
    type = SnaxType.ERROR,
    message = "Something went wrong!",
    actionTitle = "Retry",
    action = { retryAction() }
)
```

### Integrate with UI
```kt
Snax(state = snaxState)
```
‌‌
‌‌
‌‌
## 🎨 Customize Snax component
```kt
Snax(
    state = snaxState,
    shape = RoundedCornerShape(12.dp),
    progressStyle = ProgressStyle.SYMMETRIC,
    dismissBehavior = DismissBehavior.SWIPE_HORIZONTAL
)
```

‌‌
‌‌
‌‌
## Show a custom Snax
```kt
snaxState.setData(
    type = SnaxType.CUSTOM(
        icon = R.drawable.ic_custom,
        backgroundColor = Color.Blue,
        overlayColor = Color.Cyan,
        contentColor = Color.White
    ),
    message = "Custom Snack Bar!"
)
```

‌‌
‌‌
‌‌
## 📝 License
This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.

‌‌
‌‌
‌‌
## 🤝 Contributions
Contributions, issues, and feature requests are welcome! Feel free to check the [issues](https://github.com/Milad-Mohammadi/Snax/issues) page and submit pull requests.

‌‌
‌‌
‌‌
## 📬 Contact
For any inquiries, feel free to reach out via Mohammadi.Dev@gmail.com.
