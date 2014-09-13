/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jmh.samples

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit

import java.util.concurrent.TimeUnit

class JMHSample_02_BenchmarkModes {

  /*
   * JMH generates lots of synthetic code for the benchmarks for you
   * during the benchmark compilation. JMH can measure the benchmark
   * methods in lots of modes. Users may select the default benchmark
   * mode with the special annotation, or select/override the mode via
   * the runtime options.
   *
   * With this scenario, we start to measure something useful. Note that
   * our payload code potentially throws the exceptions, and we can just
   * declare them to be thrown. If the code throws the actual exception,
   * the benchmark execution will stop with error.
   *
   * When you are puzzled with some particular behavior, it usually helps
   * to look into the generated code. You might see the code is doing not
   * something you intend it to do. Good experiments always follow up on
   * the experimental setup, and cross-checking the generated code is an
   * important part of that follow up.
   *
   * The generated code for this particular sample is somewhere at
   *  target/generated-sources/annotations/.../JMHSample_02_BenchmarkModes.java
   */

  /*
   * Mode.Throughput, as stated in its Javadoc, measures the raw throughput
   * by continuously calling the benchmark method in a time-bound iteration,
   * and counting how many times we executed the method.
   *
   * We are using the special annotation to select the units to measure in,
   * although you can use the default.
   */

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput))
  @OutputTimeUnit(TimeUnit.SECONDS)
  def measureThroughput: Unit = TimeUnit.MILLISECONDS.sleep(100)

  /*
   * Mode.AverageTime measures the average execution time, and it does it
   * in the way similar to Mode.Throughput.
   *
   * Some might say it is the reciprocal throughput, and it really is.
   * There are workloads where measuring times is more convenient though.
   */

  @Benchmark
  @BenchmarkMode(Array(Mode.AverageTime))
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  def measureAvgTime = TimeUnit.MILLISECONDS.sleep(100)

  /*
   * Mode.SampleTime samples the execution time. With this mode, we are
   * still running the method in a time-bound iteration, but instead of
   * measuring the total time, we measure the time spent in *some* of
   * the * benchmark method calls.
   *
   * This allows us to infer the distributions, percentiles, etc.
   *
   * JMH also tries to auto-adjust sampling frequency: if the method
   * is long enough, you will end up capturing all the samples.
   */
  @Benchmark
  @BenchmarkMode(Array(Mode.SampleTime))
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  def measureSamples: Unit = TimeUnit.MILLISECONDS.sleep(100)

  /*
   * Mode.SingleShotTime measures the single method invocation time. As the Javadoc
   * suggests, we do only the single benchmark method invocation. The iteration
   * time is meaningless in this mode: as soon as benchmark method stops, the
   * iteration is over.
   *
   * This mode is useful to do cold startup tests, when you specifically
   * do not want to call the benchmark method continuously.
   */

  @Benchmark
  @BenchmarkMode(Array(Mode.SingleShotTime))
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  def measureSingleShot = TimeUnit.MILLISECONDS.sleep(100)

  /*
   * We can also ask for multiple benchmark modes at once. All the tests
   * above can be replaced with just a single test like this:
   */

  @Benchmark
  @BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime))
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  def measureMultiple: Unit = TimeUnit.MILLISECONDS.sleep(100)

  /*
   * Or even...
   */

  @Benchmark
  @BenchmarkMode(Array(Mode.All))
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  def measureAll: Unit = TimeUnit.MILLISECONDS.sleep(100)

}
