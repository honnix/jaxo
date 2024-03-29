/*
 * Copyright 2012 Honnix Liang
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.honnix.jaxo.core.exception;

/**
 * The only kind of exception that will be thrown by JAXO. Define it as unchecked exception to make life easier for
 * Java programmer.
 *
 * @author honnix
 */
public class JAXOException extends RuntimeException {
    private static final long serialVersionUID = 4124910550966358658L;

    public JAXOException() {
        super();
    }

    public JAXOException(String message) {
        super(message);
    }

    public JAXOException(String message, Throwable cause) {
        super(message, cause);
    }

    public JAXOException(Throwable cause) {
        super(cause);
    }
}
