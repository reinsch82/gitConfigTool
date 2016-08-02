package org.reinhold.git.ignore

import java.io.File
import java.util.regex.Pattern

class Dir {
	def dirs = []
	File dir
	def ignoreFile = [content : []]
	Dir(dir) {
		this.dir = dir
	}

	def analyzeSubTree() {
		def matches = [:]
		if(ignoreFile instanceof GitIgnore)
			print ignoreFile.file.canonicalPath + "\n"
			ignoreFile.content.each {
				if(!it.startsWith("/")) {
					matches[it] = findMatches(it)
				}
     			matches.each {
				it.value.each { val ->
					if(!val.key.equals(ignoreFile.file.canonicalPath)) {
						print " " + it.key "=" + it.toString() + "\n"
					}
				}
			}
		}
	}
	
	def analyzeTree() {
		analyzeSubTree()
		
		dirs.each {
			it.analyzeTree()
		}
	}
	
	def findMatches(pattern) {
		def p = pattern.replace(".", "\\.")
		p = p.replaceAll("\\*\\*", "(([^/]*)/)*.*")
		p = p.replaceAll("\\*", ".*")

		def matches = [:]
		ignoreFile.content.each {
			if(Pattern.matches(p, it)) {
				matches[ignoreFile.file.getCanonicalPath()] = it
			}
		}
		dirs.each { matches += it.findMatches(p) }
		return matches
	}	
	def String toString() {
		dir.canonicalPath
	}
}
