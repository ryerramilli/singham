var gulp = require('gulp');
var zip = require('gulp-zip');
var runSequence = require('run-sequence');
var del = require('del');
var fs = require("fs");

var pkgContent = fs.readFileSync("./src/package.json");
var pkg = JSON.parse(pkgContent);

var artifact = pkg.name + "_" + pkg.version + ".zip";

var publishFolder = "./__publish";

gulp.task('clean', function() {
  return del([publishFolder, artifact]);
});

gulp.task('publish', function(callback) {

  return gulp.src(['./src/**/*', '!./src/node_modules/**/*', '!./src/node_modules', '!./src/gulpfile.js'])
    .pipe(gulp.dest(publishFolder));

});

gulp.task('publish', function(callback) {

  return gulp.src(['./src/**/*', '!./src/node_modules/**/*', '!./src/node_modules', '!./src/gulpfile.js'])
    .pipe(gulp.dest(publishFolder));

});

gulp.task('package', function() {

  return gulp.src([ publishFolder + '/**/*'])
    .pipe(zip(artifact))
    .pipe(gulp.dest('.'));
});

gulp.task('default', function(callback) {

  return runSequence(
    ['clean'],
    ['publish'],
    ['package'],
    callback
  );

});
