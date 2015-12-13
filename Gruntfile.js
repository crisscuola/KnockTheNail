module.exports = function (grunt) {

    grunt.initConfig({
        shell: {
            options: {
                stdout: true,
                stderr: true
            },
            server: {
                command: 'java -cp server.jar main.Main 8080'
            }
        },
        fest: {
            templates: {
                files: [{
                    expand: true,
                    cwd: 'templates',
                    src: '*.xml',
                    dest: 'public_html/js/tmpl'
                }],
                options: {
                    template: function (data) {
                        return grunt.template.process(
                            'define(function () { return <%= contents %> ; });',
                            {data: data}
                        );
                    }
                }
            }
        },
        watch: {
            fest: {
                files: ['templates/*.xml'],
                tasks: ['fest'],
                options: {
                    interrupt: true,
                    atBegin: true
                }
            },
            server: {
                files: [
                    'public_html/js/**/*.js',
                    //'public_html/css/**/*.css'
                ],
                options: {
                    livereload: true
                }
            },
	    sass: {
            files: ['public_html/**/*.scss'],
            tasks: ['sass'],
            options: {
                livereload: true
            }
	    }
        },
        concurrent: {
            target: ['watch', 'shell'],
            options: {
                logConcurrentOutput: true
            }
        },
	sass: {
	    dist: {
		options: {
        	style: 'compressed'
      	    	},
	        files: [{
		    expand: true,
		    cwd: 'public_html/css',
		    src: ['*.scss'],
		    dest: 'public_html/css',
		    ext: '.css'
	        }]
	    }
	},
	requirejs: {
	    build: {
  		options: {
    		    almond: true,
    		    baseUrl: "public_html/js",
    		    mainConfigFile: "public_html/js/main.js",
    		    name: "main",
    		    optimize: "none",
    		    out: "public_html/js/build/main.js"
  	   	} 
	    }
	},
	concat: {
	    build: {
    		separator: ';\n',
    		src: [
          	    'public_html/js/lib/almond.js',
          	    'public_html/js/build/main.js'
    		],
    		dest: 'public_html/js/build.js'
	    },
	    css: {
		separator: ';\n',
                src: [
                    'public_html/css/bootstrap.min.css',
                    'public_html/css/base.css' 
                ],
                dest: 'public_html/css/build.css'
	    }
	},
	uglify: {
	    build: {
    		files: {
        	    'public_html/js/build.min.js': 
              	    ['public_html/js/build.js']
    		}
	    }
	}
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-concurrent');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-fest');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-requirejs');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-concat');
    
    grunt.registerTask('default', ['concurrent', 'sass']);
    grunt.registerTask(
    	'build',
    	[
            'fest', 'requirejs:build',
            'concat:build', 'concat:css', 
	    'uglify:build'
    	]
    );
};
