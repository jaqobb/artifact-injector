/*
 * This file is a part of dinjector, licensed under the MIT License.
 *
 * Copyright (c) Jakub Zagórski (jaqobb)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.jaqobb.dinjector.repository;

import java.util.Objects;

public final class Repository {
  public static Repository of(final String url) {
    if(url == null) {
      throw new NullPointerException("Url cannot be null");
    }
    return new Repository(url);
  }

  private final String url;

  private Repository(final String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }

  @Override
  public boolean equals(final Object object) {
    if(this == object) {
      return true;
    }
    if(object == null || this.getClass() != object.getClass()) {
      return false;
    }
    final Repository that = (Repository) object;
    return Objects.equals(this.url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.url);
  }
}