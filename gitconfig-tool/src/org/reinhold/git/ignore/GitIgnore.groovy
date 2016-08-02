package org.reinhold.git.ignore

class GitIgnore {
	File file
	Set content = []
	GitIgnore(file) {
		this.file = file
		this.file.eachLine { line ->
			content.add(line)
		}
	}
	def String toString() {
		file.canonicalPath
	}
}
