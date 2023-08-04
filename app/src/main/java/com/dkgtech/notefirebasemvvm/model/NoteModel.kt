package com.dkgtech.notefirebasemvvm.model

data class NoteModel(
    val title: String = "",
    val desc: String = ""
) {
    var docId: String = ""
}
