# boot-build-info
Simple build info generator basing on git and user's defined build data.

This task is merely inspired by [thread on HackerNews] (http://hn.premii.com/#/comments/11528177) and bases on [build_info.clj gist](https://gist.github.com/emidln/8f5993a37ff300e36897debe9c5bf558)
shared by one of commentators.

    $ boot -h
    Generates build information composed of git sha/branch and user's defined data

    Options:
      -h, --help              Print this help info.
      -d, --dest DESTINATION  Set location where build info json will be created to DESTINATION.
      -b, --build BUILD=VAL   Conj [BUILD VAL] onto additional build data



```dest``` should be relative to project root directory and should contain file name as well, eg: ```data/build.json```.
By default it's set to ```resources/build.json```.

```build``` is a key-string value map with additional info that should be attached to build info (like environment, tags, etc.)

## Why?

Because sometimes it's crucial to know what your production is running on. Having simple json with basic build info (git branch, sha of last commit...)
exposed eg. by REST endpoint may save your life in case of emergency.

## What's in by default?

With no additional configuration following information is attached to build.json:

    {:sha (git rev-parse --short HEAD)
     :branch (git rev-parse --abbrev-ref HEAD)
     :timestamp (now-iso-str)}

## Sample configuration

    (def +version+ "1.0.0")

    (task-options!
        build-info {:build {:env "prod" :version +version+}})

this results in something like this in your ```resource/build.json```:

    # boot build-info

    {"sha":"07bae1e","branch":"master","timestamp":"2016-04-20T10:04:24.586Z","env":"prod","version":"0.1.0"}

## LICENSE

Copyright © Michał Buczko

Licensed under the EPL.
