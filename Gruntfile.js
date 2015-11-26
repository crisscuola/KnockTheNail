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
	        files: [{
		    expand: true,
		    cwd: 'public_html/css',
		    src: ['*.scss'],
		    dest: 'public_html/css',
		    ext: '.css'
	        }]
	    }
	}
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-concurrent');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-fest');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.registerTask('default', ['concurrent', 'sass']);

};
