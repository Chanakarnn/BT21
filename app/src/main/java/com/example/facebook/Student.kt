package com.example.facebook

class Student(var name: String, var score: Float) {

    companion object {
        fun getSampleStudentData(size: Int): ArrayList<Student> {
            val student: ArrayList<Student> = ArrayList()
            for (i in 0 until size) {
                student.add(Student("Sec $i", Math.random().toFloat() * 100))
            }
            return student
        }
    }

}
